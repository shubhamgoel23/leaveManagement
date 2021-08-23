package com.leave.management.scheduler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LeaveUpdateProcessor {

	private static String fileName = "D:\\Leaves.csv";

	public void executeLeaveUpdateJob(CSVObject<EmployeeDto> employees) {
		log.info("Processing Leaves");
		CSVObject<LeavesDto> leavesObject = new CSVObject<>(LeavesDto.class, fileName);
		List<LeavesDto> leaves = leavesObject.getList();
		Map<String, EmployeeDto> cache = employees.getCache();

		for (LeavesDto leave : leaves) {
			if (leave.getEmployeeId().equals("empId"))
				continue;
			EmployeeDto emp = cache.get(leave.getEmployeeId());
			if (null == emp)
				continue;
			if (Integer.valueOf(emp.getAvailableLeaves()) >= Integer.valueOf(leave.getAppliedLeaves())) {
				System.out.println(emp.getName() + " is eligible for the leave.");
				emp.setAvailableLeaves(String.valueOf(
						Integer.valueOf(emp.getAvailableLeaves()) - Integer.valueOf(leave.getAppliedLeaves())));
				emp.setLeavesTaken(String
						.valueOf(Integer.valueOf(emp.getLeavesTaken()) + Integer.valueOf(leave.getAppliedLeaves())));
			}

			else {
				System.out.println(emp.getName() + " is not eligible for the leave.");
			}

		}
		leaves.removeIf(l -> !l.getEmployeeId().equals("empId"));

		// update leaves csv
		leavesObject.updateData(leaves);

		// update employee csv
		employees.updateData(employees.getList().stream().map(e -> {
			e.setAvailableLeaves(cache.get(e.getEmployeeId()).getAvailableLeaves());
			e.setLeavesTaken(cache.get(e.getEmployeeId()).getLeavesTaken());
			return e;
		}).collect(Collectors.toList()));

	}

}
