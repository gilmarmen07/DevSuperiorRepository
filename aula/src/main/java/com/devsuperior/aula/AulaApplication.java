package com.devsuperior.aula;

import com.devsuperior.entities.Order;
import com.devsuperior.services.OrderService;
import com.devsuperior.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan({"com.devsuperior"}) // Also search this package
public class AulaApplication implements CommandLineRunner {

	@Autowired
	private ShippingService shippingService;
	@Autowired
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(AulaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		System.out.println("Code: ");
		String code = sc.nextLine();
		System.out.println("Basic: ");
		Double basic = sc.nextDouble();
		System.out.println("Discount: ");
		Double discount = sc.nextDouble();

		Order order = new Order(code, basic, discount);
		System.out.println("Order code: "+code);
		System.out.printf("Total value: R$ %.2f%n ", orderService.total(order));
	}
}
