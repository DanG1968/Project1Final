import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home-component/home-component';
import { AuthComponent } from './components/auth-component/auth-component';
import { EmployeeComponent } from './components/employee-component/employee-component';
import { ListEmployeesComponent } from './components/list-employees-component/list-employees-component';
import { NavbarComponent } from './components/navbar-component/navbar-component';
import {RouterModule} from '@angular/router';
import {appRoutes} from './routes';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import {FormsModule} from '@angular/forms';
import {WelcomeComponent} from './components/welcome/welcome.component';
import {ErrorComponent} from './components/error/error.component';
import {FooterComponent} from './components/footer/footer.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {HttpIntercepterBasicAuthService} from './services/http-interceptor-basic-auth.service';
import { ListReimbursementsComponent } from './components/list-reimbursements/list-reimbursements.component';
import { ReimbursementComponent } from './components/reimbursement/reimbursement.component';
import {Interceptor} from './services/login/interceptor.service';
import { MyReimbursementsComponent } from './components/my-reimbursements/my-reimbursements.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AuthComponent,
    EmployeeComponent,
    ListEmployeesComponent,
    NavbarComponent,
    LoginComponent,
    LogoutComponent,
    WelcomeComponent,
    ErrorComponent,
    FooterComponent,
    ListReimbursementsComponent,
    ReimbursementComponent,
    MyReimbursementsComponent
  ],
  imports: [
    RouterModule.forRoot(appRoutes),
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
     {provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
