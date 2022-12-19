package net.dahliasolutions.varcomp;

import net.dahliasolutions.varcomp.connectors.CompanyConnector;
import net.dahliasolutions.varcomp.connectors.EmployeeConnector;
import net.dahliasolutions.varcomp.connectors.PositionsConnector;
import net.dahliasolutions.varcomp.models.Employee;

import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeUtils {

    public static Integer getEmployeeShares(LocalDate startDate, Integer StartingShares, Integer positionID) {
        // get vars
        Integer today = LocalDate.now().getYear();
        Integer hire = startDate.getYear();
        int additionAssignments = 0;
        Integer yearAssign = VarComp.getCurrentCompany().getShares_issued_years();
        Integer sharesAssign = VarComp.getCurrentCompany().getShares_issued_amount();
        double shareMultiplier = PositionsConnector.getPosition(positionID).getPosition_shares();
        //run calc
        int additionalShares = (int) (sharesAssign * shareMultiplier);
        try {
            additionAssignments = ((today - hire) / yearAssign);
        } catch (Exception e) {
            System.out.println(e);
        }
        return (additionalShares * additionAssignments) + StartingShares;
    }

    public static void updateAllEmployeeShares() {
        ArrayList<Employee> employeeArrayList = EmployeeConnector.getEmployees(true);
        for ( Employee employee : employeeArrayList ) {
            employee.setShares_assigned(getEmployeeShares(
                    employee.getStart_date(), employee.getStarting_shares(), employee.getPosition()
            ));
            EmployeeConnector.updateEmployee(employee);
        }
    }

    public static void setCompanySharesAssigned() {
        Integer sharesAssigned = 0;
        ArrayList<Employee> employeeArrayList = EmployeeConnector.getEmployees(true);
        for ( Employee employee : employeeArrayList ) {
            sharesAssigned += employee.getShares_assigned();
        }
        VarComp.getCurrentCompany().setShares_outstanding(sharesAssigned);
        CompanyConnector.updateCompany(VarComp.getCurrentCompany());
    }

}
