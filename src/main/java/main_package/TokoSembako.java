package main_package;

import static main_package.Attributes.*;
import static main_package.CustomerProcessor.*;
import static main_package.TransactionProcessor.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import dao.ProductDao;
import dao.TransactionDao;

public class TokoSembako {

	public static void start() {
		ProductDao productDao = new ProductDao();
		ArrayList<Product> productArr = productDao.getProducts();

		ArrayList<ShoppingCart> shoppingCart = new ArrayList<ShoppingCart>();

		String productId;
		String productName;
		double productPrice;
		int productStock;

		Product product;
		int productIndex;
		int quantity;

		boolean run = true;

		while (run) {
			productIndex = printProductsAndGetIndex(productArr);
			product = productArr.get(productIndex);

			productId = product.getProductId();
			productName = product.getProductName();
			productPrice = product.getProductPrice();
			productStock = product.getProductStock();

			quantity = getQuantity();

			if (quantity < productStock) {
				shoppingCart.add(new ShoppingCart(productId, productName, quantity, productPrice));
				product.decrementStock(quantity);
				run = askRun();
			} else if (quantity > productStock) {
				System.out.println("Jumlah melebihi stock barang");
			}
		}
		
		printShoppingCart(shoppingCart);

		promptEnterKey();

		HashMap<String, Object> transactionData = getTransactionData(shoppingCart);
		Customer customer = ask();

		String[] de = makeTransaction(transactionData, customer);

		printPaymentCode(de[0]);

		process(de[1]);

	}

	private static int printProductsAndGetIndex(ArrayList<Product> product) {
		System.out.println("*************************************");
		System.out.println("*			Toko Sembako            *");
		System.out.println("*************************************");
		
		for (Product i : product) {
			System.out.println(i.getproductIndex() + ". " + i.getProductName() + " Stock: " + i.getProductStock() 
						+ " Harga: "+ i.getProductPrice());
		}
		System.out.println("*************************************");
		
		boolean run = true;
		int productInput = 0;
		
		while(run) {
			System.out.print("Pilih barang: ");

			Scanner sc = new Scanner(System.in);
			productInput = sc.nextInt();

			if(productInput > product.size()) {
				System.out.println("Invalid");
			}else {
				run = false;
			}
		
		}
		
		return productInput - 1;
	}

	private static int getQuantity() {
		System.out.println("Jumlah: ");
		Scanner sc = new Scanner(System.in);
		int quantity = sc.nextInt();

		return quantity;
	}

	private static boolean askRun() {
		String runAgain;
		Scanner sc = new Scanner(System.in);
		boolean run = true;

		System.out.println("Ketik Y/y untuk beli barang lagi \n Ketik N/n untuk lanjut ke pembayaran");
		while (run) {
			runAgain = sc.next().trim().toLowerCase();
			if (runAgain.equals("y")) {
				run = false;
				return true;
			} else if (runAgain.equals("n")) {
				run = false;
				return false;
			} else {
				System.out.println("Input invalid");
			}
		}
		return false;
	}

	private static HashMap<String, Object> getTransactionData(ArrayList<ShoppingCart> shoppingCart) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String productIds = "";
		String productPrices = "";
		String productQuantities = "";
		double transactionAmount = 0;

		for (ShoppingCart cart : shoppingCart) {
			productIds += cart.getProductId() + ", ";
			productPrices += cart.getProductPrice() + ", ";
			productQuantities += cart.getQuantity() + ", ";
			transactionAmount += cart.getProductPrice() * cart.getQuantity();

			map.put(PRODUCT_IDS, productIds);
			map.put(PRODUCT_PRICES, productPrices);
			map.put(PRODUCT_QUANTITIES, productQuantities);
			map.put(TRANSACTION_AMOUNT, transactionAmount);
		}
		return map;
	}

	private static void printShoppingCart(ArrayList<ShoppingCart> shoppingCart) {
		int count = 1;
		for (ShoppingCart cart : shoppingCart) {
			System.out.println(count + ". Nama produk: " + cart.getProductName());
			System.out.println("Harga per produk: " + cart.getProductPrice());
			System.out.println("Jumlah barang: " + cart.getQuantity());
			System.out.println("Total harga produk: " + cart.getTotalPrice());
			System.out.println(" ");
			count++;
		}
	}

	private static void printPaymentCode(String paymentCode) {
		System.out.print("Payment Code: ");
		System.out.println(paymentCode);
		promptEnterKey();
	}

	private static void promptEnterKey() {
		System.out.println("Press \"ENTER\" to continue...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
	}

}
