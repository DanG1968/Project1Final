import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {BasicAuthenticationService} from '../../services/basic-authentication.service';
import {HardcodedAuthenticationService} from '../../services/hardcoded-authentication.service';
import {LoginService} from '../../services/login/login-service';
import {Token} from '../../services/login/token';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = '';
  password = '';
  errorMessage = 'Invalid Credentials';
  invalidLogin = false;

 constructor(private router: Router, private loginService: LoginService,
             private hardcodedAuthenticationService: HardcodedAuthenticationService,
             private basicAuthenticationService: BasicAuthenticationService) { }

  ngOnInit() {
  }

  login() {
    this.loginService.login({ username: this.username, password: this.password, rememberMe: true })
      .subscribe(
        (token: Token) => {
          localStorage.setItem('authToken', token.idToken);
          const url = 'welcome/' + this.username;
          this.router.navigate([url]);
        },
        err => {
          console.log(err.status);
        }
      );
  }


  handleLogin() {
    if (this.hardcodedAuthenticationService.authenticate(this.username, this.password)) {

      this.router.navigate(['welcome', this.username]);
      this.invalidLogin = false;
    } else {
      this.invalidLogin = true;
    }
  }

  handleBasicAuthLogin() {

    this.basicAuthenticationService.executeAuthenticationService(this.username, this.password)
      .subscribe(
        data => {
          console.log(data);
          this.router.navigate(['welcome', this.username]);
          this.invalidLogin = false;
        },
        error => {
          console.log(error);
          this.invalidLogin = true;
        }
      );
  }

  handleJWTAuthLogin() {
    this.basicAuthenticationService.executeJWTAuthenticationService(this.username, this.password)
      .subscribe(
        data => {
          console.log(data);
          this.router.navigate(['welcome', this.username]);
          this.invalidLogin = false;
        },
        error => {
          console.log(error);
          this.invalidLogin = true;
        }
      );
  }

}
