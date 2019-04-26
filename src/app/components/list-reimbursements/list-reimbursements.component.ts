import { Component, OnInit } from '@angular/core';
import {ReimbursementService} from '../../services/reimbursement.service';
import {Router} from '@angular/router';

export class Reimbursement {

  constructor(
    public id: string,
    public name: string,
    public description: string,
    public amount: number,
    public date: string,
    public approved: number,
    public empId: number
  ) {
  }
}

@Component({
  selector: 'app-list-reimbursements',
  templateUrl: './list-reimbursements.component.html',
  styleUrls: ['./list-reimbursements.component.css']
})
export class ListReimbursementsComponent implements OnInit {

  constructor(
    private reimbursementService: ReimbursementService,
    private router: Router
  ) {
  }

  reimbursements: Reimbursement[];

  message: string;



  ngOnInit() {
    this.refreshReimbursements();
  }

  refreshReimbursements() {
    this.reimbursementService.retrieveAllReimbursements().subscribe(
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
        this.refreshReimbursements();
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
