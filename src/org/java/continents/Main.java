package org.java.continents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:8889/db_nations";
        String user = "root";
        String password = "root";
        Scanner scan = new Scanner(System.in);



        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("inserisca la nazione da ricercare");

            String userNameCountry = scan.nextLine();

            String query =
                    "select c2.name , c2.country_id , r.name as region , c.name as continent \n" +
                            "from continents c \n" +
                            "join regions r on c.continent_id = r.continent_id \n" +
                            "join countries c2 on c2.region_id = r.region_id \n" +
                            "where c2.name like ? " +
                            "order by c2.name   ";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1,"%" + userNameCountry + "%");



                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int countryID = rs.getInt("country_id");
                        String countryName= rs.getNString("name");
                        String regionName = rs.getNString("region");
                        String continentName = rs.getNString("continent");







                        System.out.println(countryID + " " + countryName + "  " + regionName + " " + continentName);
                    }



                }



            }



        }



        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
