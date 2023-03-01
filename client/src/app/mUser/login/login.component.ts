import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../core/auth/auth.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})

export class LoginComponent implements OnInit {

  isPasswordHidden: boolean = true;
  isLoginDisabled: boolean = true;
  hasBadCredentials = false;

  loginForm: FormGroup = new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });

  constructor(private router: Router,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    // this.authService.login();
    // this.router.navigateByUrl('/');

    this.loginForm.statusChanges.subscribe(formStatus => this.isLoginDisabled = formStatus == 'INVALID');
  }

  onSubmit() {

    //todo implement. hasBadCredentials changes on failed login
    this.hasBadCredentials = true
    this.loginForm.controls['password'].setValue('');
    console.log(this.loginForm.value)


  }
}
