package services;

import collection.MyArrayList;
import exceptions.MyException;
import io.FileScan;
import io.Scannable;
import productclasses.*;
import utils.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * class for working with database
 */
public class DataBase {
    private static final String url = "jdbc:postgresql://localhost:9999/studs";
    private static String login = "login";
    private static String password = "password";

    private static Connection connection;
    private static boolean isConnected = false;
    private static final String IS_EXIST_REQUEST = "SELECT * FROM users WHERE login LIKE ?";
    private static final String IS_AUTH_REQUEST = "SELECT * FROM users WHERE login LIKE ? AND password LIKE ?";
    private static final String INSERT_USER_REQUEST = "INSERT INTO users (login, password) VALUES (?,?)";
    private static final String GET_COLLECTION = "SELECT * FROM products";
    private static final String SAVE_COLLECTION = "INSERT INTO products" +
            "(product_name," +
            "coord_x," +
            "coord_y," +
            "creation_date," +
            "price," +
            "unit_of_measure," +
            "person_name," +
            "person_pass_id," +
            "person_eye_color," +
            "person_hair_color," +
            "person_country," +
            "product_owner)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * reading DB log in data
     * @throws IOException
     * @throws MyException
     */
    private static void readLoginDetails() throws IOException, MyException {
        String filename = System.getenv("DBPASS");
        Scannable fileReader = new FileScan(filename);
        String[] data = fileReader.readLine().split(";");
        login = data[0];
        password = data[1];
        fileReader.close();
    }

    /**
     * connect with database
     */
    private static void connect() {
        try {
            readLoginDetails();
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, login, password);
            isConnected = true;

        } catch (Exception e) {
            System.out.println("Connection fault: " + e.getMessage());
        }
    }

    /**
     * check, does user already logged in database
     * @param user
     * @return
     */
    public static boolean isUserExist(User user) {
        try {
            PreparedStatement checkStatement = connection.prepareStatement(IS_EXIST_REQUEST);
            checkStatement.setString(1, user.login);
            ResultSet resultSet = checkStatement.executeQuery();
            return (resultSet.next());
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * register new user - add it in database
     * @param user
     * @return
     */
    public static DBResponse registerUser(User user) {
        if (!isConnected) connect();
        if (!isConnected) return new DBResponse(false, "Connection fault.");
        if (isUserExist(user)) return new DBResponse(false, "User with this login already exists.");
        try {
            String hashPassword = HashCipher.encodeMD2(user.password);
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_USER_REQUEST);
            insertStatement.setString(1, user.login);
            insertStatement.setString(2, hashPassword);
            insertStatement.executeUpdate();
            insertStatement.close();
            return new DBResponse(true, "User successfully registered.");
        } catch (SQLException e) {
            e.printStackTrace();
            return new DBResponse(false, "Failed to get user data.: " + e.getMessage());
        }
    }

    /**
     * checking user password
     * @param user
     * @return
     */
    public static DBResponse isUserAuth(User user) {
        if (!isConnected) connect();
        if (!isConnected) return new DBResponse(false, "Connection fault.");
        if (!isUserExist(user)) return new DBResponse(false, "There is no user with this login.");
        try {
            String hashPassword = HashCipher.encodeMD2(user.password);

            PreparedStatement checkPasswordState = connection.prepareStatement(IS_AUTH_REQUEST);
            checkPasswordState.setString(1, user.login);
            checkPasswordState.setString(2, hashPassword);

            ResultSet checkSet = checkPasswordState.executeQuery();
            if (checkSet.next())
                return new DBResponse(true, "User verified.");
            else return new DBResponse(false, "Wrong password.");
        } catch (SQLException e) {
            e.printStackTrace();
            return new DBResponse(false, "Failed to get user data.: " + e.getMessage());
        }
    }

    /**
     * get all collection from database
     * @return
     */
    public static MyArrayList<Product> getCollection() {
        if (!isConnected) connect();
        MyArrayList<Product> products = new MyArrayList<>();
        try {
            ResultSet collection = connection.prepareStatement(GET_COLLECTION).executeQuery();
            while (collection.next()) {
                Product product = new Product();
                product.setId(collection.getInt(1));
                product.setName(collection.getString(2));

                Coordinates coordinates = new Coordinates();
                coordinates.setX(collection.getFloat(3));
                coordinates.setY(collection.getInt(4));
                product.setCoordinates(coordinates);

                LocalDateTime tmpTime = collection.getTimestamp(5).toLocalDateTime();
                ZonedDateTime zonedDateTime = tmpTime.atZone(ZoneId.systemDefault());
                product.setCreationDate(zonedDateTime);

                product.setPrice(collection.getInt(6));

                UnitOfMeasure unit = UnitOfMeasure.valueOf(collection.getString(7));
                product.setUnitOfMeasure(unit);

                Person owner = new Person();
                owner.setName(collection.getString(8));
                owner.setPassportID(collection.getString(9));

                Color eyeColor = (collection.getString(10) == null) ? null
                        : Color.valueOf(collection.getString(10));
                owner.setEyeColor(eyeColor);

                Color hairColor = (collection.getString(11) == null) ? null
                        : Color.valueOf(collection.getString(11));
                owner.setHairColor(hairColor);

                Country country = (collection.getString(12) == null) ? null
                        : Country.valueOf(collection.getString(12));
                owner.setNationality(country);

                product.setOwner(owner);
                product.setUser(collection.getString(13));
                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Reading fault: " + e.getMessage());
        } catch (MyException e) {
            System.out.println("Wrong data: " + e.getMessage());
        }
        return products;
    }

    /**
     * save single product
     * @param product
     */
    public static void saveProduct(Product product) {
        if (!isConnected) connect();
        try {
            PreparedStatement savedProduct = connection.prepareStatement(SAVE_COLLECTION);
            savedProduct.setString(1, product.getName());
            savedProduct.setFloat(2, product.getCoordinates().getX());
            savedProduct.setInt(3, product.getCoordinates().getY());

            Timestamp time = Timestamp.valueOf(product.getCreationDate().toLocalDateTime());
            savedProduct.setTimestamp(4, time);
            savedProduct.setInt(5, product.getPrice());
            savedProduct.setString(6, product.getUnitOfMeasure().toString());

            Person owner = product.getOwner();
            savedProduct.setString(7, owner.getName());
            savedProduct.setString(8, owner.getPassportID());
            savedProduct.setString(9, (owner.getEyeColor() == null) ? null : owner.getEyeColor().toString());
            savedProduct.setString(10, (owner.getHairColor() == null) ? null : owner.getHairColor().toString());
            savedProduct.setString(11, (owner.getNationality() == null) ? null : owner.getNationality().toString());
            savedProduct.setString(12, product.getUser());

            savedProduct.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Saving error: " + e.getMessage());
        }
    }

    /**
     * save all collection of products
     * @param collection
     */
    public static void saveCollection(MyArrayList<Product> collection){
        try {
            connection.createStatement().executeUpdate("TRUNCATE products");
            for (Product product : collection)
                saveProduct(product);
        } catch (SQLException e) {
            System.out.println("Saving error: " + e.getMessage());
        }
    }

    /**
     * closing connection
     */
    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Closing error: " + e.getMessage());
        }
    }
}
