package home.learning.designprinciples.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class BookInventoryApplication {

    Properties properties;
    List<Book> books = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);


    public void loadApplicationPreferences(String fileLocation) throws IOException{
    	
        properties = new Properties();
        properties.load(getClass().getResourceAsStream("SystemPreference.properties"));
        loadDefaultBooksFromApplication();
    }

    public static void main(String[] args) throws IOException {
        BookInventoryApplication application = new BookInventoryApplication();
        application.loadApplicationPreferences(null);

        application.verifyCredentials();
        System.out.println();

        while (true) {
            int userInput = application.getInputFromUser();
            if (userInput == 1) {
                application.addBook();
            } else if (userInput == 2) {
                application.deleteBook();
            } else {
                application.displayBooks();
            }
        }

    }

    private void deleteBook(){
        System.out.print("Enter book Name to delete :");
        String bookName = null;
        deleteBook: while (true) {
            try {
                bookName = scanner.nextLine();
                if (bookName == null || bookName.equalsIgnoreCase("")) {
                    System.err.print("Invalid Book Name, Try Again:");
                    continue;
                }
                for (Book book : this.books) {
                    if (book.getBookName().equalsIgnoreCase(bookName)) {
                        this.removeBook(book);
                        System.out.println("|==============================================|");
                        System.out.println("|      Book Deleted successfully!!   :)        |");
                        System.out.println("|==============================================|");
                        System.out.println();
                        break deleteBook;
                    }
                }
                System.err.print("Invalid Book Name, Try Again:");
                continue deleteBook;

            } catch (Exception e) {
                System.err.print("Invalid Book Name, Try Again:");
                continue;
            }
        }
    }


    private void addBook(){
        Book book = new Book();
        scanner.delimiter();
        System.out.print("Enter book name : ");
        String bookName = null;
        while (true) {
            bookName = scanner.nextLine();
            if (bookName == null || bookName.equalsIgnoreCase("")) {
                System.err.println("Invalid book Name, Try again:");
                continue ;
            } else {
                break;
            }
        }

        double bookPrice = 0.0;
        System.out.print("Enter book price :");
        bookPrice: while (true) {
            try {
                String price = scanner.next();
                bookPrice = Double.parseDouble(price);
                break bookPrice;
            } catch (Exception e) {
                System.err.print("Invalid price , try again:");
                continue bookPrice;
            }
        }

        System.out.print("Enter Quantity :");
        int bookQuantity = 0;
        while (true) {
            try {
                bookQuantity = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.err.print("Invalid Quantity , try again:");
                continue;
            }
        }

        book.setBookName(bookName);
        book.setBookPrice(bookPrice);
        book.setNumberOfBooks(bookQuantity);
        this.books.add(book);
        System.out.println("|==============================================|");
        System.out.println("|      Book Added successfully!!   :)          |");
        System.out.println("|==============================================|");
        System.out.println();
    }
    private int getInputFromUser(){
        System.out.print("1.) Press 1 for add a book,");
        System.out.print("2.) Press 2 to delete a book,");
        System.out.print("3.) Press 3 to display inventory.");
        System.out.println();
        System.out.print("Please provide your input : ");
        int input =  0;
        while(true){
            try{
                input = scanner.nextInt();
                if(input ==0 || input >3){
                    System.err.print("Invalid Number , try again:");
                    continue;
                }else {
                    break;
                }
            }catch(Exception e){
                System.err.print("Invalid Number , try again:");
                continue;
            }

        }
        scanner.nextLine(); //nextInt() does not consume the new line character given .https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
        return input;
    }

    public String getUserName() {
        return properties.getProperty("userName");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

    List<String> getBooks(){
        String books=  properties.getProperty("books");
        if(books != null){
            return Arrays.asList(books.split(","));
        }
        return new ArrayList<>();
    }

    void verifyCredentials() {
        System.out.print("Enter your UserName[Case Insensitive]:");
        String userName = scanner.next();
        userName = (userName != null)?userName:""; // To avoid Null pointer exception
        System.out.print("Enter the password[Case Sensitive]:");
        String password = scanner.next();
        password = (password != null)?password:"";// To avoid Null pointer exception

        String systemUserName = this.getUserName();
        String systemPassword = this.getPassword();


        if(!userName.equalsIgnoreCase(systemUserName) || !password.equals(systemPassword)){
            System.err.println("|==============================================|");
            System.err.println("|      Invalid credentials!!   :'(             |");
            System.err.println("|==============================================|");
            System.exit(1);
        }
        System.out.println();
    }

    private void loadDefaultBooksFromApplication(){
        List<String> bookList= this.getBooks();
        bookList.forEach(name -> {
            Book book = new Book();
            book.setBookName(name);
            book.setBookPrice(100);
            book.setNumberOfBooks(1);
            books.add(book);
        });
    }

    public  boolean addBook(Book book){
        return books.add(book);
    }

    public  boolean removeBook(Book book){
        return books.remove(book);
    }


    public void displayBooks(){
        System.out.println("BOOK INVENTORY:");
        System.out.println("-----------------------------------------------");
        System.out.println("| Name                 |  Price  |  Quantity |");
        System.out.println("-----------------------------------------------");
        books.forEach(book -> {
            System.out.print("|");
            System.out.print(book.getBookName());
            printWhiteSpace(22, book.getBookName().length());
            System.out.print("|");
            System.out.print(book.getBookPrice());
            printWhiteSpace(9, String.valueOf(book.getBookPrice()).length());
            System.out.print("|");
            System.out.print(book.getNumberOfBooks());
            printWhiteSpace(11,String.valueOf(book.getNumberOfBooks()).length());
            System.out.print("|");
            System.out.println();
            System.out.println("-----------------------------------------------");
        });
        System.out.println();
    }

    //FOR UI DECORATION:
    private  static void printWhiteSpace(int totalSpace, int totalFilled){
        int totalSpaceToPrint = totalSpace - totalFilled;
        for (int i = 0; i < totalSpaceToPrint; i++) {
            System.out.print(" ");
        }
    }


}



