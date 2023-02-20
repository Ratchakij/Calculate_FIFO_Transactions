public class Transaction {
	public String order;
	public String coin_name;
	public double price;
	public double coin_qty;
	public Transaction() {
	};
	public Transaction(String xorder, String xcoin_name, double xprice, double xcoin_qty) {
		order = xorder;
		coin_name = xcoin_name;
		price = xprice;
		coin_qty = xcoin_qty;
	}
}
/*
B BTC 	680000.0 	2.5 
B ETH 	43000.0 	12.0 
B BTC 	690000.0 	2.5 
S BTC 	695000.0 	3.0 
B ETH 	43500.0 	23.5 
S BTC 	695000.0 	1.0 
S ETH 	45000.0 	30.0 
============================
B	BTC	680000.000000	2.500000
B	BTC	690000.000000	2.500000
B	ETH	43000.000000	12.000000
B	ETH	43500.000000	23.500000
============================
S	BTC	695000.000000	3.000000
S	BTC	695000.000000	1.000000
S	ETH	45000.000000	30.000000
============================

ส่วนแรก (ขายจากข้อ A. 2 เหรียญ) กำไร (180,000 - 100,000) x 2 = 160,000 บาท 
ส่วนที่สอง (ขายจากข้อ B. 1 เหรียญ) ขาดทุน (200,000 - 180,000) x 1 = 20,000 บาท 
รวมทั้งสิ้น กำไร = 160,000 - 20,000 = 140,000 บาท 
*/