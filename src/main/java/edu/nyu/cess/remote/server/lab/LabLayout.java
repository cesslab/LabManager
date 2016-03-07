package edu.nyu.cess.remote.server.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aruff on 2/24/16.
 */
public class LabLayout
{
	public List<Row> rows;

	public List<Row> getRows()
	{
		return rows;
	}

	public void setRows(List<Row> rows)
	{
		this.rows = rows;
	}

	public ArrayList<Computer> getAllComputers()
	{
		ArrayList<Computer> computerList = new ArrayList<>();
		for (Row row : getRows()) {
			for (Computer computer : row.getComputers()) {
				computerList.add(computer);
			}
		}

		return computerList;
	}

    public HashMap<String, Computer> getComputersByIp()
    {
        HashMap<String, Computer> computerMap = new HashMap<>();
        for (Row row : getRows()) {
            for (Computer computer : row.getComputers()) {
                computerMap.put(computer.getIp(), computer);
            }
        }

        return computerMap;
    }
}
