package server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Client implements Runnable{
    private List<List<String>> data = new ArrayList<>();
    private BufferedReader reader;
    private static List<String> imgList = new ArrayList<>();

    private String login;


    public Client(Socket socket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


    }

    private void parseMessage(String message){



        List<String> lineData = Arrays.stream(message.split(",")).toList();
//        System.out.println(lineData);
        data.add(lineData);
    }

    public void generate(int index) throws IOException {
        List<String> dataLine = data.get(index);
        BufferedImage image = new BufferedImage(100, 200, BufferedImage.TYPE_INT_ARGB);
//        System.out.println(dataLine.size());
        for(int i = 0; i < 200; i++){
            for(int j = 0; j < 100; j++){
                image.setRGB(j, i, 0xffffffff);
                int y0 = image.getHeight() / 2;
                float fy = Float.parseFloat(dataLine.get(j));
//                System.out.println(fy);
                int y = (int)(fy + y0);
                image.setRGB(j, y, 0xffff0000);
            }
        }

        ImageIO.write(image, "png", new File("C:\\Users\\Vilembraw\\IdeaProjects\\PClient\\tmp\\obrazek.png"));
        File file = new File("C:\\Users\\Vilembraw\\IdeaProjects\\PClient\\tmp\\data.txt");
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println(encodeBase64(image));
        writer.close();
//        System.out.println(encodeBase64(image));
    }

    private static String encodeBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
//        System.out.println("encode");
        imgList.add(base64Image);
        return base64Image;
    }

    public void addRecord(String Login,int electrode,String image){
        String url = "jdbc:sqlite:C:\\Users\\Vilembraw\\IdeaProjects\\PClient\\usereeg.db";
        String insertDataSql = "INSERT INTO user_eeg (username, electrode_number, image) VALUES(\""+login+"\","+electrode+",\""+image+"\")";
        try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
            stmt.execute(insertDataSql);
            System.out.println("Inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run(){
        String message = "asd";
        int c = 0;

        try {
            this.login = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {

            while((message = reader.readLine())!=null){
                if(message.equals("Bye")){
                    break;
                }


                parseMessage(message);
                generate(c);
                addRecord(login,c,imgList.get(c));

//                System.out.println(data.get(data.size()-1));
                c++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
