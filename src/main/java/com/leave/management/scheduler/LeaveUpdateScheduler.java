package com.leave.management.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LeaveUpdateScheduler {

	private final LeaveUpdateProcessor leaveUpdateProcessor;
	private static String fileName = "D:\\EmployeeData.csv";
	private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	// check for leaves every 10 seconds
	@PostConstruct
	public void scheduleJob() {
		// creates employee data at startup
		CSVObject<EmployeeDto> employees = new CSVObject<>(EmployeeDto.class, fileName);
		// process leaves
		scheduler.scheduleAtFixedRate(() -> leaveUpdateProcessor.executeLeaveUpdateJob(employees), 10l, 10l,
				TimeUnit.SECONDS);
	}
}
