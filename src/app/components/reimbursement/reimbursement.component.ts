import { Component, OnInit } from '@angular/core';
import {Reimbursement} from '../list-reimbursements/list-reimbursements.component';
import {ReimbursementService} from '../../services/reimbursement.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-reimbursement',
  templateUrl: './reimbursement.component.html',
  styleUrls: ['./reimbursement.component.css']
})
export class ReimbursementComponent implements OnInit {

  id: string;
  reimbursement: Reimbursement;

  constructor(
    private reimbursementService: ReimbursementService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit() {

    console.log('In reimbursement component init');
    this.id = this.route.snapshot.params.id;

    this.reimbursement = new Reimbursement(this.id, '', '', 0, '', 0, 0);
    console.log('now', this.id);

    if (this.id !== '-1') {
      this.reimbursementService.retrieveReimbursement(this.id)
        .subscribe(
          data => {
            if (data != null) {
              this.reimbursement = data;
              console.log('From Backend: ' + data);
            }
          }
        );
    }
  }

  saveReimbursement() {
    if (this.reimbursement.id == '-1') {
      console.log(-1);
      this.reimbursementService.createReimbursement(this.reimbursement)
        .subscribe(
          data => {
            console.log(data);
            this.router.navigate(['list-reimbursements']);
          }
        );
    } else {
      this.updateReimbursement();
    }
  }

  updateReimbursement() {
    this.reimbursementService.updateReimbursement(this.id, this.reimbursement)
      .subscribe(
        data => {
          console.log(data);
          this.router.navigate(['list-reimbursements']);
        }
      );
  }

  deleteReimbursement() {
    this.reimbursementService.deleteReimbursement(this.id)
      .subscribe(data => {

      });
  }
}
