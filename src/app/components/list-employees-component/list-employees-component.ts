import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {EmployeeService} from '../../services/employee-service';

export class Employee {
  constructor(
    public employeeId: string,
    public firstName: string,
    public lastName: string,
    public email: string,
    public managerId: number
  ) {
  }
}

@Component({
  selector: 'app-list-employees-component',
  templateUrl: './list-employees-component.html',
  styleUrls: ['./list-employees-component.css']
})
export class ListEmployeesComponent implements OnInit {


  employees: Employee[];

  message: string;

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.refreshEmployees();
  }

  refreshEmployees() {
    this.employeeService.retrieveAllEmployees().subscribe(
      response => {
        console.log(response);
        this.employees = response;
      }
    );
  }

  deleteEmployee(id) {
    console.log(`delete employee ${id}`);
    this.employeeService.deleteEmployee(id).subscribe(
      (response: any) => {
        this.message = `Delete of Employee with ID=${response.id} Successful!`;
        this.refreshEmployees();
      }
    );
  }

  updateEmployee(id) {
    console.log(`update ${id}`);
    this.router.navigate(['employee', id]);
  }

  addEmployee() {
    console.log(`add new employee`);
    this.router.navigate(['employee', -1]);
  }
}
