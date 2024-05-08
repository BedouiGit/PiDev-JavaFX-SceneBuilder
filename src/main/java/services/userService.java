package services;


import org.mindrot.jbcrypt.BCrypt;
import models.Role;
import models.user;
import utils.MyDB;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.sql.DriverManager.getConnection;


public class userService  implements IUser<user>{
    private final MyDB myConnection = MyDB.getInstance();
    private final Connection connection = myConnection.getConnection();

    public boolean UserExistsById(int id) {
        String query = "SELECT COUNT(*) FROM user WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean UserExistsByEmail(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE Email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public String getVerificationCodeByEmail(String email) {
        String query = "SELECT verification_code FROM user WHERE Email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("verification_code");
            }
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public boolean ajouterUser(user user) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String query = "INSERT INTO user(first_name,last_name,email, password, roles, age,address,tel,gender) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
        if (UserExistsByEmail(user.getEmail())) {
            System.out.println("User already exists!");
            return false;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirst_name());
            preparedStatement.setString(2, user.getLast_name());
            preparedStatement.setString(3, user.getEmail());
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            preparedStatement.setString(4, hashedPassword);
            String s = "[\"" + user.getRole().toString().toUpperCase() + "\"]";
            preparedStatement.setString(5, s);

            preparedStatement.setInt(6, user.getAge());
            preparedStatement.setString(7, user.getAddress());
            preparedStatement.setString(8, user.getTel());
            preparedStatement.setString(9, user.getGender());
            preparedStatement.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }


    public List<user> afficherUser() {
        List<user> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user user = new user();
                user.setId(resultSet.getInt("ID"));
                user.setFirst_name(resultSet.getString("First Name"));
                user.setLast_name(resultSet.getString("Last Name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.valueOf(resultSet.getString("Role")));
                user.setAge(resultSet.getInt("age"));
                user.setAddress(resultSet.getString("Address"));
                user.setTel(resultSet.getString("Phone Number"));
                user.setGender(resultSet.getString("gender"));

                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public  void changePasswordById(int userId,String newPassword){
        String query = "UPDATE `user` SET `password`=? WHERE `id`=? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setInt(2, userId);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with the given ID.");
            }
        }catch(SQLException ex){
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void modifierUser(user user) {
        String query = "UPDATE `user` SET `email`=?, `first_name`=?, `last_name`=?, `address`=?, `age`=?, `gender`=?, `tel`=? WHERE `id`=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirst_name());
            preparedStatement.setString(3, user.getLast_name());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setInt(5, user.getAge());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.setString(7, user.getTel());
            //preparedStatement.setString(8, user.getRole().name());
            preparedStatement.setInt(8, user.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public void deleteUser(user user) {
        String query = "DELETE FROM user WHERE Id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("No user found with the given ID.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public user getUserById(int userId) {
        user user = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new user();
                user.setId(resultSet.getInt("Id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                //user.setRole(Role.valueOf(resultSet.getString("roles")));

                String roleString = resultSet.getString("roles");

                // Map the role string to the corresponding Role enum constant
                Role role;
                if ("ROLE_ADMIN".equals(roleString)) {
                    role = Role.ROLE_ADMIN;
                } else if ("ROLE_USER".equals(roleString)) {
                    role = Role.ROLE_USER;
                } else {
                    // Handle the case when the role string does not match any enum constant
                    // You can set a default role or log a warning here
                    role = Role.ROLE_USER; // Setting default role to ROLE_USER
                    // Alternatively, you can throw an exception or handle the situation as per your requirement
                    // throw new IllegalArgumentException("Invalid role value from database: " + roleString);
                }

                user.setRole(role);
                user.setFirst_name(resultSet.getString("first_name"));
                user.setLast_name(resultSet.getString("last_name"));
                user.setAge(resultSet.getInt("age"));
                user.setTel(resultSet.getString("tel"));
                user.setGender(resultSet.getString("gender"));
                user.setAddress(resultSet.getString("address"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public int getIdByEmail(String email) {
        int userId = -1; // Default value indicating no user found with the provided email

        String query = "SELECT Id FROM user WHERE Email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("Id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userId;
    }
    public List<user> getAll() {
        List<user> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `user` ";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                user u = new user(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),

                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("roles").replaceAll("[\"\\[\\]]", "")),

                        rs.getInt("age"),
                        rs.getString("tel"),

                        rs.getString("address"),
                        rs.getString("gender")
                        //rs.getString("verification_code")
                );
                list.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }


    public String getRoleByEmail(String email) {
        String role = "Unknown"; // Default value

        String query = "SELECT `roles` FROM user WHERE Email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                role = resultSet.getString("roles");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return role;
    }




    public user authenticateUser(String email, String Mdp) {
        user user = null;
        try {
            String query = "SELECT * FROM user WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, Mdp);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new user();
                user.setId(resultSet.getInt("Id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.valueOf(resultSet.getString("roles")));
                user.setFirst_name(resultSet.getString("first_name"));
                user.setLast_name(resultSet.getString("last_name"));
                user.setAge(resultSet.getInt("age"));
                user.setTel(resultSet.getString("tel"));
                user.setGender(resultSet.getString("gender"));
                user.setAddress(resultSet.getString("address"));
                System.out.println("User Retrieved: " + user.toString());
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean verifyCode(String code) {
        boolean isValid = false;
        String query = "SELECT * FROM user WHERE verification_code = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isValid = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isValid;
    }

    public user getUserByEmail(String text) {
        user user = null;
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, text);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                user = new user();
                user.setId(resultSet.getInt("Id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                //user.setRole(Role.valueOf(resultSet.getString("roles")));
                String roleString = resultSet.getString("roles");
                roleString = roleString.replaceAll("[\\[\\]\"]", ""); // Remove square brackets and quotes
                Role role = Role.valueOf(roleString);
                user.setRole(role);

                user.setFirst_name(resultSet.getString("first_name"));
                user.setLast_name(resultSet.getString("last_name"));
                user.setAge(resultSet.getInt("age"));
                user.setTel(resultSet.getString("tel"));
                user.setGender(resultSet.getString("gender"));
                user.setAddress(resultSet.getString("address"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public void changePasswordByEmail(String text, String hashedPassword) {
        String query = "UPDATE user SET password = ? WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, text);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateImage(user user, String image) {
        String query = "UPDATE user SET profile_picture = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, image);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateProfile(user user) {
        try {
            // Update user information
            String updateUserQuery = "UPDATE user SET email=?, profile_picture=?, roles=? WHERE id=?";
            PreparedStatement updateUserStatement = connection.prepareStatement(updateUserQuery);
            updateUserStatement.setString(1, user.getEmail());
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            updateUserStatement.setString(2, hashedPassword);
            String s = "[\"" + user.getRole().toString().toUpperCase() + "\"]";
            updateUserStatement.setString(4, s);
            updateUserStatement.setString(1, user.getFirst_name());
            updateUserStatement.setString(4, user.getLast_name());
            updateUserStatement.setInt(1, user.getAge());
            updateUserStatement.setString(1, user.getTel());
            updateUserStatement.setString(1, user.getGender());
            updateUserStatement.setString(1, user.getAddress());
            int userRowsUpdated = updateUserStatement.executeUpdate();
            updateUserStatement.close();


            if (userRowsUpdated > 0 ) {
                System.out.println("Profile updated successfully!");
            } else {
                System.out.println("Failed to update profile.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(userService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


/*
    //stat/////////
    public Map<Date, Integer> getUserDataByDate() {
        Map<Date, Integer> userDataByDate = new HashMap<>();
        String query = "SELECT date, COUNT(*) AS user_count FROM user GROUP BY date";

        try (
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Date dateRegistered = resultSet.getDate("date");
                int userCount = resultSet.getInt("user_count");
                userDataByDate.put(dateRegistered, userCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }

        return userDataByDate;
    }
*/


    public Map<String, Integer> getUserDataByStatus() {
        Map<String, Integer> userDataByStatus = new HashMap<>();
        String query = "SELECT is_banned, COUNT(*) AS status_count FROM user GROUP BY is_banned";

        try (
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int isBanned = resultSet.getInt("is_banned");
                String status = (isBanned == 1) ? "Active" : "Inactive";
                int statusCount = resultSet.getInt("status_count");
                userDataByStatus.put(status, statusCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
        }

        return userDataByStatus;
    }

    public user searchByEmail(String email) throws SQLException {
        String req = "SELECT * FROM user WHERE email=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, email);
        ResultSet res = pre.executeQuery();

        while (res.next()) {
            user u = new user();
            u.setId(res.getInt("id"));
            u.setFirst_name(res.getString("first_name"));
            u.setLast_name(res.getString("last_name"));
            u.setAddress(res.getString("address"));
            u.setEmail(res.getString("email"));
            u.setPassword(res.getString("password"));

            // Trim the role value to remove any extra characters
            String roleString = res.getString("roles").trim();

            // Convert the trimmed role value to the Role enum
            Role role = Role.valueOf(roleString); // Assuming Role is your enum class
            u.setRole(role);

            // Assuming tel is stored as an integer in the database and setAge expects an integer parameter
            u.setAge(res.getInt("tel"));

            return u; // Return the first user found
        }

        return null; // Return null if no user found
    }


}