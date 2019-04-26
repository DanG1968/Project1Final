import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Employee} from '../list-employees-component/list-employees-component';
import {EmployeeService} from '../../services/employee-service';

@Component({
  selector: 'app-employee-component',
  templateUrl: './employee-component.html',
  styleUrls: ['./employee-component.css']
})
export class EmployeeComponent implements OnInit {
  id: string;
  employee: Employee;

  constructor(
    private employeeService: EmployeeService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit() {

    console.log('In employee component init');
    this.id = this.route.snapshot.params.employeeId;

    this.employee = new Employee(this.id, '', '', '', 0);
    console.log('now', this.id);

    if (this.id !== '-1') {
      this.employeeService.retrieveEmployee(this.id)
        .subscribe(
          data => {
            if (data != null) {
              this.employee = data;
              console.log('From Backend: ' + data);
            }
          }
        );
    }
  }

  saveEmployee() {
    if (this.employee.employeeId == '-1') {
      console.log(-1);
      this.employeeService.createEmployee(this.employee)
        .subscribe(
          data => {
            console.log(data);
            this.router.navigate(['list-employees']);
          }
        );
    } else {
      this.updateEmployee();
    }
  }

  updateEmployee() {
    this.employeeService.updateEmployee(this.id, this.employee)
      .subscribe(
        data => {
          console.log(data);
          this.router.navigate(['list-employees']);
        }
      );
  }

  deleteEmployee() {
    this.employeeService.deleteEmployee(this.id)
      .subscribe(data => {

      });
  }
}
