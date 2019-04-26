import { Injectable } from '@angular/core';
import {ERS_JPA_API_URL} from '../app.constants';
import {HttpClient} from '@angular/common/http';
import {Employee} from '../components/list-employees-component/list-employees-component';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(
    private http: HttpClient
  ) { }

  retrieveAllEmployees() {
    return this.http.get<Employee[]>(`${ERS_JPA_API_URL}/api/employees`);

  }

  deleteEmployee(id) {
    return this.http.delete(`${ERS_JPA_API_URL}/api/employees/delete?id=${id}`);
  }

  retrieveEmployee(id) {
    console.log('inside retrieve employee');
    return this.http.get<Employee>(`${ERS_JPA_API_URL}/api/employees/id?id=${id}`);
  }

  updateEmployee(id, employee) {
    return this.http.put(
      `${ERS_JPA_API_URL}/api/employees`
      , employee);
  }

  createEmployee(employee) {
    return this.http.post(
      `${ERS_JPA_API_URL}/api/employees/add`
      , employee);
  }

}
