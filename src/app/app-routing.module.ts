import {RouteGuardService} from './services/route-guard.service';
import {RouterModule, Routes} from '@angular/router';
import {LogoutComponent} from './components/logout/logout.component';
import {LoginComponent} from './components/login/login.component';
import {NgModule} from '@angular/core';
import {ErrorComponent} from './components/error/error.component';
import {WelcomeComponent} from './components/welcome/welcome.component';
import {ListEmployeesComponent} from './components/list-employees-component/list-employees-component';
import {EmployeeComponent} from './components/employee-component/employee-component';
import {ReimbursementComponent} from './components/reimbursement/reimbursement.component';
import {ListReimbursementsComponent} from './components/list-reimbursements/list-reimbursements.component';


const routes: Routes = [
  { path: '', component: LoginComponent  },
  { path: 'login', component: LoginComponent },
  { path: 'employee', component: EmployeeComponent, canActivate: [RouteGuardService] },
  { path: 'reimbursement', component: ReimbursementComponent, canActivate: [RouteGuardService] },
  { path: 'reimbursement/:id', component: ReimbursementComponent, canActivate: [RouteGuardService] },
  { path: 'list-employees', component: ListEmployeesComponent, canActivate: [RouteGuardService] },
  { path: 'list-reimbursements', component: ListReimbursementsComponent, canActivate: [RouteGuardService] },
  { path: 'welcome/:name', component: WelcomeComponent, canActivate: [RouteGuardService]},
  { path: 'welcome', component: WelcomeComponent, canActivate: [RouteGuardService]},
  { path: 'logout', component: LogoutComponent, canActivate: [RouteGuardService] },
  { path: 'employee/:employeeId', component: EmployeeComponent, canActivate: [RouteGuardService]  },
  { path: '**', component: ErrorComponent }
   ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
