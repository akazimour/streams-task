package com.epam.rd.autocode.assessment.basics.collections;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.epam.rd.autocode.assessment.basics.entity.BodyType;
import com.epam.rd.autocode.assessment.basics.entity.Client;
import com.epam.rd.autocode.assessment.basics.entity.Order;
import com.epam.rd.autocode.assessment.basics.entity.Vehicle;

public class Agency implements Find, Sort, Serializable {

	private List<Vehicle> vehicles;

	private List<Order> orders;

	public Agency() {
		vehicles = new ArrayList<>();
		orders = new ArrayList<>();
	}
	public void addVehicle(Vehicle vehicle) {
		this.vehicles.add(vehicle);
	}

	public void addOrder(Order order) {
		this.orders.add(order);
	}

	@Override
	public List<Vehicle> sortByID() {

		return vehicles.stream().sorted(new Comparator<Vehicle>() {
			@Override
			public int compare(Vehicle o1, Vehicle o2) {
				return Long.compare(o1.getId(), o2.getId());
			}
		}).collect(Collectors.toList());
	}

	@Override
	public List<Vehicle> sortByYearOfProduction() {

		return vehicles.stream().sorted(Comparator.comparingInt(Vehicle::getYearOfProduction)).collect(Collectors.toList());
	}

	@Override
	public List<Vehicle> sortByOdometer() {

		return vehicles.stream().sorted(Comparator.comparingLong(Vehicle::getOdometer)).collect(Collectors.toList());

	}

	@Override
	public Set<String> findMakers() {

		return vehicles.stream().map(Vehicle::getMake).collect(Collectors.toSet());

	}

	@Override
	public Set<BodyType> findBodytypes() {

		return vehicles.stream().map(Vehicle::getBodyType).collect(Collectors.toSet());

	}

	@Override
	public Map<String, List<Vehicle>> findVehicleGrouppedByMake() {

		return vehicles.stream().collect(Collectors.groupingBy(Vehicle::getMake));

	}

	@Override
	public List<Client> findTopClientsByPrices(List<Client> clients, int maxCount) {

		return clients.stream().filter(client -> orders.stream()
						.anyMatch(order -> order.getClientId() == client.getId()))
				.toList().stream().sorted(new Comparator<Client>() {
					@Override
					public int compare(Client o1, Client o2) {
						return o2.getBalance().compareTo(o1.getBalance());
					}
				}).toList().stream().limit(maxCount).collect(Collectors.toList());

	}

	@Override
	public List<Client> findClientsWithAveragePriceNoLessThan(List<Client> clients, int average) {
		return clients.stream()
				.filter(client -> {
					Map<Long, BigDecimal> bgDec = new HashMap<>();
					Map<Long, List<Order>> collect = orders.stream().filter(order -> order.getClientId() == client.getId()).collect(Collectors.groupingBy(Order::getClientId));
					List<BigDecimal> bigDecimalListToClient = collect.entrySet().stream().map(longListEntry ->
							{
								BigDecimal reduce = longListEntry.getValue().stream().map(Order::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
								int size = longListEntry.getValue().size();
								reduce = reduce.divide(BigDecimal.valueOf(size),MathContext.DECIMAL64.getRoundingMode());
								Long key = longListEntry.getKey();
								bgDec.put(key,reduce);
								return reduce;
							}).toList();
					List<Boolean> collect1 = bgDec.entrySet().stream().map(longBigDecimalEntry -> {
						boolean b = longBigDecimalEntry.getValue().compareTo(BigDecimal.valueOf(average)) >= 0;
						return b;
					}).toList();
					List<BigDecimal> b = bgDec.values().stream().filter(bigDecimal -> bigDecimal.compareTo(BigDecimal.valueOf(average))>=0).toList();
					return bgDec.values().stream().anyMatch(bigDecimal -> bigDecimal.compareTo(BigDecimal.valueOf(average)) >= 0);

				})
				.collect(Collectors.toList());

	}

	@Override
	public List<Vehicle> findMostOrderedVehicles(int maxCount) {

		return orders.stream()
				.collect(Collectors.groupingBy(Order::getVehicleId, Collectors.summingInt(order -> 1)))
				.entrySet().stream()
				.sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
				.limit(maxCount+1)
				.map(entry -> vehicles.stream().filter(vehicle -> vehicle.getId() == entry.getKey()).findFirst().orElse(null))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

}