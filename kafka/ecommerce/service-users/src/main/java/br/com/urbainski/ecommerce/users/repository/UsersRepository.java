package br.com.urbainski.ecommerce.users.repository;

import br.com.urbainski.ecommerce.commons.user.Users;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepository {

    private final Connection connection;

    public UsersRepository(Connection connection) {
        this.connection = connection;
    }

    public boolean save(Users users) {
        var sql = "insert into Users (uuid, email) values (?,?)";

        try (var ps = this.connection.prepareStatement(sql)) {
            ps.setString(1, users.getUuid());
            ps.setString(2, users.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean existsUsersByEmail(String email) {
        var sql = "select count(*) from Users where email = ?";

        try (var ps = this.connection.prepareStatement(sql)) {
            ps.setString(1, email);

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            return false;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Users> findAll() {
        var sql = "select uuid, email from Users";

        try (var ps = this.connection.prepareStatement(sql)) {

            var result = new ArrayList<Users>();

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var uuid = rs.getString(1);
                    var email = rs.getString(2);
                    result.add(new Users(uuid, email));
                }
            }

            return result;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
