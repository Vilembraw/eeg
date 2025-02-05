import client.Client;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static server.Client.*;

public class testSendData {
    @ParameterizedTest
    @CsvSource({
            //      username,filepath,electrode,image
            "s235,C:\\Users\\Vilembraw\\IdeaProjects\\PClient\\test01.csv,0,iVBORw0KGgoAAAANSUhEUgAAAMgAAABkCAYAAADDhn8LAAACSUlEQVR4Xu3X3W6CUBCFUd//pdvaxAjjYQPKj8BaSS8E1BNnvovefoBBt3oBeBIIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAhuP7e/Rh5/QE8/EJFAz7MIgcCLfhEigR6BQPBaw56RTPlfaOw+LOh10/ZawG4cQ2cYuw8L+44tq0tfX9drrfvf7Ihn5l97WlsOcmhx6lLVZ+rrIa33bqmef8+zMFt7UlsNcWxhxpZq6PpDfX96di31e/c8C7MNT+mTxarvHftL0jNDn9H6/Pp6ben76vlaz/AVhidTBzh1kPX5sb9P1c9Kn9+6tpahMzzUc6Zn2c20iUwd4pRn1lDPl75/7P4S5n7H1LOzuXnTSENM97Yw9funPPOpd7/j3fexmvnTaC1i69pV+R1O5b1JdoM4YhxrnfeIvwXR+5M8ahx3a5z5qL8FkWkuQRyndd2JLrXQ4ji160516lKPBZDucXgmm3TjaIXQusapXHu6acFrFGOvOaVrTzcteOteN4rWfU7HhOcSx6WYcmvZW9e4JFvQiqF1jUuyBXfdIMRBh0246/5fIRA6bMKDOGiwDRAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAgEv4jyF+CFw02XAAAAAElFTkSuQmCC",
            "s345,C:\\Users\\Vilembraw\\IdeaProjects\\PClient\\test02.csv,0,iVBORw0KGgoAAAANSUhEUgAAAMgAAABkCAYAAADDhn8LAAACsklEQVR4Xu3W0Y7aMBRFUf7/p1uhCk3qiU/sxIANa0k8kNhRBt9d9fYHqLqVF4AfAoFAIBAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCMYHcrv9fGBxY6d4G4dA+ABjp3gbhkj4AOMmuAyi/A4LGjfBguADjZnoWhy167CIMdNbC6F2HRZxuzzEV/fDxAQCwbXJbomjZQ1M6t/knh3iln0ta2BS5wM5swcWc27CH3G0BtKzFibyM7U9Q9yz9q53PUyiP5DWdfAB+ia9979WsLj2Sb8ax5W98Cb/T2wa4nSvxdX9n2D7j8y3/xaLaAukdp12ZRx+zyW0ndKoAx31nNXsRVF+Z0q/T6g8uPL7FSOftZK9v3svGqbz+3S2B+cQn8tvO73903lmHM945syO/t6j+7xV/WSedXBlfOVndr3vebT+6D5v9Z6TKaMoP7Pqfc/R63i5uU5l5kEp42h5z9HreLn5TmXWYSnfq/x+1ejnMcR8J7LKoBy959H90mN9zx6ebs7TmG1QzrzPq/bwVHOexmyDUnuf2nU+xryne3X4Hvu3n9Fqz61dZznznuLZISujKD+9zuw7s4cpzX2KvYOWYqhdP3K05+g+S5v7ZHuGumXt0f0zymeW31na/Cd5NHDbMNK6h9Z1d61ry3do2cMS1jjJ2tCdGcqe9b3rWtezjDVOsxzA8tPryt6a0c9jCuucaBnF1YE8eka6x9f47glIkdSu81VMwF4k5Xe+lim420ayFwxfyxQ8iIMdJgECgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAgEAoFAIBAIBAKBQCAQCAQCgUAgEAj+AlER0heZY2jGAAAAAElFTkSuQmCC"

    })

    public void testSD(String username, String filepath, int electrode, String image) throws InterruptedException, IOException {
        imgList.clear();
        Client.sendData(username, filepath);

        String url = "jdbc:sqlite:C:\\Users\\Vilembraw\\IdeaProjects\\PClient\\usereeg.db";
        String sql = "SELECT image FROM user_eeg WHERE username =\"" + username + "\" AND electrode_number =" + electrode;
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            assertEquals(image, rs.getString("image"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}