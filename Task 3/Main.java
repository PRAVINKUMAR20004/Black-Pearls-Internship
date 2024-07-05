import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String ISBN;
    private double price;

    public Book(String title, String author, String ISBN, double price) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", ISBN=" + ISBN + ", price=" + price + "]";
    }
}

class Customer {
    private String name;
    private String email;
    private String address;

    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Customer [name=" + name + ", email=" + email + ", address=" + address + "]";
    }
}

class Order {
    private Customer customer;
    private List<Book> books;
    private double totalAmount;

    public Order(Customer customer) {
        this.customer = customer;
        this.books = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public void addBook(Book book) {
        books.add(book);
        totalAmount += book.getPrice();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Order [customer=" + customer + ", books=" + books + ", totalAmount=" + totalAmount + "]";
    }
}

class ShoppingCart {
    private List<Book> books;

    public ShoppingCart() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Book book : books) {
            total += book.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "ShoppingCart [books=" + books + "]";
    }
}

class Bookstore {
    private Map<String, Book> inventory;
    private Map<String, Customer> customers;

    public Bookstore() {
        inventory = new HashMap<>();
        customers = new HashMap<>();
    }

    public void addBook(Book book) {
        inventory.put(book.getISBN(), book);
    }

    public Book getBookByISBN(String ISBN) {
        return inventory.get(ISBN);
    }

    public List<Book> browseBooks() {
        return new ArrayList<>(inventory.values());
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getEmail(), customer);
    }

    public Customer getCustomerByEmail(String email) {
        return customers.get(email);
    }

    public Order placeOrder(Customer customer, ShoppingCart cart) {
        Order order = new Order(customer);
        for (Book book : cart.getBooks()) {
            order.addBook(book);
        }
        return order;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bookstore bookstore = new Bookstore();

        // Add some books to the bookstore
        bookstore.addBook(new Book("Effective Java", "Joshua Bloch", "9780134685991", 45.00));
        bookstore.addBook(new Book("Clean Code", "Robert C. Martin", "9780132350884", 40.00));

        // User inputs customer details
        System.out.println("Enter customer name:");
        String name = scanner.nextLine();
        System.out.println("Enter customer email:");
        String email = scanner.nextLine();
        System.out.println("Enter customer address:");
        String address = scanner.nextLine();

        Customer customer = new Customer(name, email, address);
        bookstore.addCustomer(customer);

        ShoppingCart cart = new ShoppingCart();

        // User browses and adds books to the cart
        System.out.println("Books available in the bookstore:");
        List<Book> books = bookstore.browseBooks();
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }

        while (true) {
            System.out.println("Enter the number of the book to add to the cart (0 to finish):");
            int choice = scanner.nextInt();
            if (choice == 0) break;
            if (choice > 0 && choice <= books.size()) {
                cart.addBook(books.get(choice - 1));
                System.out.println("Book added to cart.");
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        // Place an order
        Order order = bookstore.placeOrder(customer, cart);

        // Print out the details
        System.out.println("\nCustomer information:");
        System.out.println(customer);

        System.out.println("\nBooks in the shopping cart:");
        for (Book book : cart.getBooks()) {
            System.out.println(book);
        }

        System.out.println("\nPlaced order:");
        System.out.println(order);

        scanner.close();
    }
}
