package com.leave.management.scheduler;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class CSVObject<T extends Employee> {

	private Class<T> reference;
	private Map<String, T> map = new HashMap<>();
	private List<T> list = new ArrayList<>();
	private final String fileName;

	public CSVObject(Class<T> classRef, String fileName) {
		this.reference = classRef;
		this.fileName = fileName;
		buildCache();
	}

	private void buildCache() {

		try {
			list = new CsvToBeanBuilder<T>(new FileReader(fileName)).withType(reference).build().parse();

			map = list.stream().collect(Collectors.toMap(T::getEmployeeId, Function.identity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, T> getCache() {
		return map;
	}

	public List<T> getList() {
		return list;
	}

	public void updateData(List<T> list) {
		try {
			FileWriter writer = new FileWriter(fileName);

			StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
			beanToCsv.write(list);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
