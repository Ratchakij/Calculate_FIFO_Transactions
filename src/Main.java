import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Main {
	private static ArrayList<Transaction> transList = new ArrayList<Transaction>();
	private static ArrayList<Coin> walletList = new ArrayList<Coin>();
	private static ArrayList<Transaction> buyList = new ArrayList<Transaction>();
	private static ArrayList<Transaction> sellList = new ArrayList<Transaction>();
	private static Transaction parse(String line) {
		Transaction trans = new Transaction();
		String[] ss = line.split(" ");
		trans.order = ss[0];
		trans.coin_name = ss[1];
		trans.price = Double.parseDouble(ss[2]);
		trans.coin_qty = Double.parseDouble(ss[3]);
		return trans;
	};
	public static void getList(ArrayList<Transaction> trans) {
		for (Transaction list : trans) {
			System.out.println(String.format("%s\t%s\t%f\t%f", list.order, list.coin_name, list.price, list.coin_qty));
		} ;
	}
	public static void sort(ArrayList<Transaction> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).coin_name.compareTo(list.get(j).coin_name) > 0) {
					Collections.swap(list, i, j);
				}
			}
		}
	}
	public static void getWallet() {
		System.out.println("Your Wallet");
		for (Coin list : walletList) {
			System.out.println(list.coinName + " = " + list.coinqty);
		} ;
	};
	public static void setWallet(String sCoin, double sQty) {
		System.out.println("Your Wallet");
		for (int i = 0; i < walletList.size(); i++) {
			if (sCoin.equals(walletList.get(i).coinName)) {
				walletList.get(i).coinqty -= sQty;
			} ;
		}
		for (Coin list : walletList) {
			System.out.println(list.coinName + " = " + list.coinqty);
		} ;
	};
	public static void main(String[] args) {
		File file = new File("D:crypto_tax.txt");
		Scanner fileSc = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			fileSc = new Scanner(br);
			while (fileSc.hasNextLine()) {
				String s = fileSc.nextLine();
				//				System.out.println(s);
				transList.add(parse(s));
			} ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			fileSc.close();
		}
		for (Transaction list : transList) {
			if (list.order.equals("B")) {
				buyList.add(list);
				Coin w = new Coin(list.coin_name, list.coin_qty);
				int count = 0;
				for (int i = 0; i < walletList.size(); i++) {
					if (w.coinName.equals(walletList.get(i).coinName)) {
						walletList.get(i).coinqty += w.coinqty;
						count += 1;
						break;
					}
				}
				if (count == 0) walletList.add(w);
			} else {
				sellList.add(list);
			}
		}
		getWallet();
		System.out.println("===============================================");
		sort(buyList);
		double profitAmount = 0;
		for (int i = 0; i < sellList.size(); i++) {
			int check = 0;
			for (int j = 0; j < buyList.size(); j++) {
				double profit = 0;
				getList(sellList);
				String sName = sellList.get(i).coin_name;
				double sPrice = sellList.get(i).price;
				double sQty = sellList.get(i).coin_qty;
				if (sQty == 0) {
					System.out.println("If Selling Coin = 0 break");
					break;
				}
				getList(buyList);
				String bName = buyList.get(j).coin_name;
				double bPrice = buyList.get(j).price;
				double bQty = buyList.get(j).coin_qty;
				if (sName.equals(bName)) {
					for (Coin list : walletList) {
						if (sName.equals(list.coinName)) {
							if (sQty > list.coinqty) {
								check = 0;
							} else {
								System.out.println("Order Success");
								check = 1;
							}
						}
					} ;
					if (check == 1) {
						if (sQty < bQty) {
							profit = sPrice > bPrice ? (sPrice - bPrice) * sQty : -((bPrice - sPrice) * sQty);
							System.out.println("profit = " + profit);
							setWallet(sName, sellList.get(i).coin_qty);
							buyList.get(j).coin_qty -= sQty;
							sellList.get(i).coin_qty = 0;
						} else {
							profit = sPrice > bPrice ? (sPrice - bPrice) * bQty : -((bPrice - sPrice) * bQty);
							System.out.println("profit = " + profit);
							setWallet(sName, bQty);
							sellList.get(i).coin_qty -= bQty;
							buyList.remove(j);
							j -= 1;
						}
					}
				}
				profitAmount += profit;
			}
			if (check == 0) {
				System.out.println("ERROR!!! Not Enough Coin!!!");
				break;
			} ;
			System.out.println("===============================================");
		}
		System.out.println(String.format("profitAmount = %,.2f", profitAmount));
		getWallet();
	}
}
