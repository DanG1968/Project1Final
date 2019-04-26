import { Component, OnInit } from '@angular/core';
import {ReimbursementService} from '../../services/reimbursement.service';
import {Router} from '@angular/router';
import {Reimbursement} from '../list-reimbursements/list-reimbursements.component';
import {Employee} from '../list-employees-component/list-employees-component';

@Component({
  selector: 'app-my-reimbursements',
  templateUrl: './my-reimbursements.component.html',
  styleUrls: ['./my-reimbursements.component.css']
})
export class MyReimbursementsComponent implements OnInit {

  empId: number;
  employee: Employee;

  constructor(
    private reimbursementService: ReimbursementService,
    private router: Router

  ) { }

  reimbursements: Reimbursement[];

  message: string;

  ngOnInit() {
    this.refreshMyReimbursements();
  }

  refreshMyReimbursements() {
    this.reimbursementService.retrieveReimbursementByEmpId(100).subscribe(
      response => {
        console.log(response);
        this.reimbursements = response;
      }
    );
  }

  deleteReimbursement(id) {
    console.log(`delete reimbursement ${id}`);
    this.reimbursementService.deleteReimbursement(id).subscribe(
      (response: any) => {
        this.message = `Delete of Reimbursemente with ID=${response.id} Successful!`;
        this.refreshMyReimbursements();
      }
    );
  }

  updateReimbursement(id) {
    console.log(`update ${id}`);
    this.router.navigate(['reimbursement', id]);
  }

  addReimbursement() {
    console.log(`add new reimbursement`);
    this.router.navigate(['reimbursement', -1]);
  }
}
