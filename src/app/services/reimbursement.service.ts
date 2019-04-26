import { Injectable } from '@angular/core';
import {ERS_JPA_API_URL} from '../app.constants';
import {HttpClient} from '@angular/common/http';
import {Reimbursement} from '../components/list-reimbursements/list-reimbursements.component';

@Injectable({
  providedIn: 'root'
})
export class ReimbursementService {

  constructor(
    private http: HttpClient
  ) { }

  retrieveAllReimbursements() {
    return this.http.get<Reimbursement[]>(`${ERS_JPA_API_URL}/api/reimbursement-requests`);

  }

  deleteReimbursement(id) {
    return this.http.delete(`${ERS_JPA_API_URL}/api/reimbursement-requests/delete?id=${id}`);
  }

  retrieveReimbursement(id) {
    console.log('inside retrieve reimbursements');
    return this.http.get<Reimbursement>(`${ERS_JPA_API_URL}/api/reimbursement-requests/my/id?id=${id}`);
  }

  retrieveReimbursementByEmpId(id) {
    return this.http.get<Reimbursement[]>(`${ERS_JPA_API_URL}/api/reimbursement-requests/my/empId?empId=${id}`);
  }

  updateReimbursement(id, reimbursement) {
    return this.http.put(
      `${ERS_JPA_API_URL}/api/reimbursement-requests`
      , reimbursement);
  }

  createReimbursement(reimbursement) {
    return this.http.post(
      `${ERS_JPA_API_URL}/api/reimbursement-requests`
      , reimbursement);
  }

}
