import java.util.ArrayList;
import java.util.Scanner;

public class HotelBooking {

    // Kelas untuk informasi pelanggan
    static class Customer {
        String name;
        String address;
        String phone;
        int roomNumber;
        String roomType;

        Customer(String name, String address, String phone, int roomNumber, String roomType) {
            this.name = name;
            this.address = address;
            this.phone = phone;
            this.roomNumber = roomNumber;
            this.roomType = roomType;
        }

        // Menampilkan detail pemesanan
        public void displayDetails() {
            System.out.println("Nama: " + name);
            System.out.println("Alamat: " + address);
            System.out.println("No Telepon: " + phone);
            System.out.println("Nomor Kamar: " + roomNumber);
            System.out.println("Tipe Kamar: " + roomType);
        }
    }

    // Kelas untuk Tipe Kamar
    static class RoomType {
        String name;
        int maxRooms;
        ArrayList<Integer> rooms;
        ArrayList<Integer> availableRooms;
        ArrayList<Customer> roomBookings;

        RoomType(String name, int startRoomNumber, int maxRooms) {
            this.name = name;
            this.maxRooms = maxRooms;
            rooms = new ArrayList<>();
            availableRooms = new ArrayList<>();
            roomBookings = new ArrayList<>();

            // Inisialisasi nomor kamar
            for (int i = 0; i < maxRooms; i++) {
                int roomNumber = startRoomNumber + i;
                rooms.add(roomNumber);
                availableRooms.add(roomNumber);
            }
        }

        // Menampilkan kamar yang tersedia
        public void displayAvailableRooms() {
            if (availableRooms.size() > 0) {
                System.out.println("Kamar yang tersedia untuk " + name + ": " + availableRooms);
            } else {
                System.out.println("Semua kamar " + name + " sudah penuh.");
            }
        }

        // Memilih kamar dan memasukkan detail pelanggan
        public boolean selectRoom(int roomNumber, String name, String address, String phone) {
            if (availableRooms.contains(roomNumber)) {
                availableRooms.remove(Integer.valueOf(roomNumber));
                Customer customer = new Customer(name, address, phone, roomNumber, this.name);
                roomBookings.add(customer);
                System.out.println("Anda telah memilih kamar nomor " + roomNumber + " (" + this.name + ").");
                return true;
            } else {
                System.out.println("Kamar nomor " + roomNumber + " sudah tidak tersedia.");
                return false;
            }
        }

        // Membatalkan pemesanan kamar dengan validasi nama dan nomor telepon
        public boolean cancelRoom(int roomNumber, String name, String phone) {
            for (Customer customer : roomBookings) {
                if (customer.roomNumber == roomNumber && customer.name.equalsIgnoreCase(name) && customer.phone.equals(phone)) {
                    System.out.println("Detail Pemesanan:");
                    customer.displayDetails();

                    // Konfirmasi pembatalan
                    System.out.print("Apakah Anda yakin ingin membatalkan kamar ini? (y/n): ");
                    Scanner scanner = new Scanner(System.in);
                    char confirm = scanner.next().charAt(0);

                    if (confirm == 'y' || confirm == 'Y') {
                        availableRooms.add(roomNumber); // Mengembalikan kamar ke daftar kamar kosong
                        roomBookings.remove(customer); // Menghapus pemesanan dari daftar pemesanan
                        System.out.println("Kamar nomor " + roomNumber + " telah dibatalkan.");
                        return true;
                    } else {
                        System.out.println("Pembatalan dibatalkan.");
                        return false;
                    }
                }
            }
            System.out.println("Nama atau nomor telepon tidak cocok dengan pemesanan kamar nomor " + roomNumber + ".");
            return false;
        }

        // Menampilkan detail pemesanan untuk semua kamar di tipe kamar ini
        public void displayAllBookings() {
            if (roomBookings.isEmpty()) {
                System.out.println("Tidak ada pemesanan untuk kamar " + name + ".");
            } else {
                for (Customer customer : roomBookings) {
                    System.out.println("\nDetail Pemesanan Kamar " + customer.roomNumber + ":");
                    customer.displayDetails();
                }
            }
        }

        // Method untuk menampilkan pemesanan berdasarkan nama dan nomor telepon (khusus untuk customer)
        public boolean displayBookingByNameAndPhone(String name, String phone) {
            for (Customer customer : roomBookings) {
                if (customer.name.equalsIgnoreCase(name) && customer.phone.equals(phone)) {
                    System.out.println("Detail Pemesanan:");
                    customer.displayDetails();
                    return true; // Jika ditemukan, kembalikan nilai true dan keluar dari method
                }
            }
            return false; // Jika tidak ditemukan, kembalikan nilai false
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inisialisasi tipe kamar dengan nomor kamar yang unik
        RoomType deluxe = new RoomType("Deluxe", 101, 10);
        RoomType standard = new RoomType("Standard", 111, 10);
        RoomType suite = new RoomType("Suite", 121, 10);

        boolean running = true;
        boolean isAdmin = false;
        boolean isCustomer = false;

        // Menu login
        while (!isAdmin && !isCustomer) {
            System.out.println("Login sebagai:");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.print("Pilih (1-2): ");
            int loginChoice = scanner.nextInt();

            if (loginChoice == 1) {
                // Admin login
                System.out.print("Masukkan password admin: ");
                String adminPassword = scanner.next();
                if (adminPassword.equals("admin123")) {
                    isAdmin = true;
                    System.out.println("Login sebagai Admin berhasil.");
                } else {
                    System.out.println("Password salah.");
                }
            } else if (loginChoice == 2) {
                // Customer login
                isCustomer = true;
                System.out.println("Login sebagai Customer berhasil.");
            } else {
                System.out.println("Pilihan tidak valid.");
            }
        }

        while (running) {
            if (isAdmin) {
                // Menu untuk admin
                System.out.println("\nMenu Admin:");
                System.out.println("1. Lihat Semua Kamar Kosong");
                System.out.println("2. Lihat Semua Detail Pemesanan");
                System.out.println("3. Keluar");
                System.out.print("Masukkan pilihan (1-3): ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Menampilkan kamar yang kosong dari semua tipe kamar
                        System.out.println("\nKamar kosong:");
                        deluxe.displayAvailableRooms();
                        standard.displayAvailableRooms();
                        suite.displayAvailableRooms();
                        break;

                    case 2:
                        // Menampilkan semua detail pemesanan dari semua tipe kamar
                        System.out.println("\nDetail Pemesanan:");
                        deluxe.displayAllBookings();
                        standard.displayAllBookings();
                        suite.displayAllBookings();
                        break;

                    case 3:
                        running = false;
                        System.out.println("Terima kasih, Admin.");
                        break;

                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } else if (isCustomer) {
                // Menu untuk customer
                System.out.println("\nMenu Customer:");
                System.out.println("1. Pesan Kamar");
                System.out.println("2. Lihat Kamar Kosong");
                System.out.println("3. Cancel Kamar");
                System.out.println("4. Lihat Detail Pemesanan Anda");
                System.out.println("5. Keluar");
                System.out.print("Masukkan pilihan (1-5): ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Pilih tipe kamar
                        System.out.println("\nPilih tipe kamar:");
                        System.out.println("1. Deluxe");
                        System.out.println("2. Standard");
                        System.out.println("3. Suite");
                        System.out.print("Masukkan pilihan (1-3): ");
                        int roomTypeChoice = scanner.nextInt();

                        RoomType selectedRoomType = null;
                        switch (roomTypeChoice) {
                            case 1:
                                selectedRoomType = deluxe;
                                break;
                            case 2:
                                selectedRoomType = standard;
                                break;
                            case 3:
                                selectedRoomType = suite;
                                break;
                            default:
                                System.out.println("Pilihan tidak valid.");
                                continue;
                        }

                        // Menampilkan kamar yang tersedia
                        selectedRoomType.displayAvailableRooms();
                        System.out.print("Masukkan nomor kamar yang ingin dipilih: ");
                        int roomNumber = scanner.nextInt();

                        System.out.print("Masukkan nama Anda: ");
                        scanner.nextLine();  // Konsumsi newline
                        String name = scanner.nextLine();

                        System.out.print("Masukkan alamat Anda: ");
                        String address = scanner.nextLine();

                        System.out.print("Masukkan nomor telepon Anda: ");
                        String phone = scanner.nextLine();

                        // Pilih kamar dengan nomor tertentu
                        selectedRoomType.selectRoom(roomNumber, name, address, phone);
                        break;

                    case 2:
                        // Menampilkan kamar yang kosong
                        System.out.println("\nKamar kosong:");
                        deluxe.displayAvailableRooms();
                        standard.displayAvailableRooms();
                        suite.displayAvailableRooms();
                        break;

                    case 3:
                        // Cancel kamar dengan validasi
                        System.out.println("\nPilih tipe kamar untuk membatalkan:");
                        System.out.println("1. Deluxe");
                        System.out.println("2. Standard");
                        System.out.println("3. Suite");
                        System.out.print("Masukkan pilihan (1-3): ");
                        int cancelRoomTypeChoice = scanner.nextInt();

                        RoomType cancelRoomType = null;
                        switch (cancelRoomTypeChoice) {
                            case 1:
                                cancelRoomType = deluxe;
                                break;
                            case 2:
                                cancelRoomType = standard;
                                break;
                            case 3:
                                cancelRoomType = suite;
                                break;
                            default:
                                System.out.println("Pilihan tidak valid.");
                                continue;
                        }

                        System.out.print("Masukkan nomor kamar yang ingin dibatalkan: ");
                        int cancelRoomNumber = scanner.nextInt();

                        // Validasi nama dan nomor telepon sebelum membatalkan
                        System.out.print("Masukkan nama Anda: ");
                        scanner.nextLine();  // Konsumsi newline
                        String cancelName = scanner.nextLine();

                        System.out.print("Masukkan nomor telepon Anda: ");
                        String cancelPhone = scanner.nextLine();

                        // Membatalkan pemesanan kamar dengan validasi
                        cancelRoomType.cancelRoom(cancelRoomNumber, cancelName, cancelPhone);
                        break;

                    case 4:
                        // Lihat detail pemesanan dengan validasi nama dan nomor telepon
                        System.out.print("Masukkan nama Anda: ");
                        scanner.nextLine();  // Konsumsi newline
                        String searchName = scanner.nextLine();

                        System.out.print("Masukkan nomor telepon Anda: ");
                        String searchPhone = scanner.nextLine();

                        // Menampilkan pemesanan yang sesuai
                        boolean found = deluxe.displayBookingByNameAndPhone(searchName, searchPhone) ||
                                        standard.displayBookingByNameAndPhone(searchName, searchPhone) ||
                                        suite.displayBookingByNameAndPhone(searchName, searchPhone);

                        if (!found) {
                            System.out.println("Tidak ditemukan pemesanan dengan nama dan nomor telepon tersebut.");
                        }
                        break;

                    case 5:
                        running = false;
                        System.out.println("Terima kasih telah menggunakan layanan kami.");
                        break;

                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            }
        }

        scanner.close();
    }
}
