import {Routes} from '@angular/router';
import {AuthComponent} from './components/auth-component/auth-component';
import {EmployeeComponent} from './components/employee-component/employee-component';
import {HomeComponent} from './components/home-component/home-component';
import {ListEmployeesComponent} from './components/list-employees-component/list-employees-component';
import {ListReimbursementsComponent} from './components/list-reimbursements/list-reimbursements.component';
import {ReimbursementComponent} from './components/reimbursement/reimbursement.component';


/*
Routing is a feature that Angular uses to acheive
true single page application format.

Routing entails setting up a page to be injected
through the URL that we are connected to. The URLs
themselves don't point to separate files to be
displayed due to the fact that this would be a
multipage application.
*/

/*NOTE: If your routes don't work, try switching the
order of your routes to match the order in which
 */

export const appRoutes: Routes = [
    {
        path: 'auth',
        component: AuthComponent
    },
    {
        path: 'employee',
        component: EmployeeComponent
    },
    {
        path: 'reimbursement',
        component: ReimbursementComponent
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'list-employees',
        component: ListEmployeesComponent
    },
    {
        path: 'list-reimbursements',
        component: ListReimbursementsComponent
    }
]
