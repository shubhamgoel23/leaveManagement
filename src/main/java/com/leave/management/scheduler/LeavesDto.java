package com.leave.management.scheduler;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeavesDto implements Employee {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CsvBindByPosition(position = 0)
	private String empId;

	@CsvBindByPosition(position = 1)
	private String appliedLeaves;

	@Override
	public String getEmployeeId() {
		return this.empId;
	}

}
