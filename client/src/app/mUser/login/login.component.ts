import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../core/user-service/user.service";
import {User} from "../../utils/types";

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
    //todo remove defaults
    email: new FormControl('haibusa2005@abv.b', Validators.required),
    password: new FormControl('123456Aa', Validators.required),
  });

  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loginForm.statusChanges.subscribe(formStatus => this.isLoginDisabled = formStatus == 'INVALID');
  }

  onSubmit() {
    let formValues = this.loginForm.value;

    this.userService.login$(formValues.email, formValues.password).subscribe(hasLoggedIn => {
      if (hasLoggedIn) {
        this.router.navigateByUrl('/');
        return;
      }
      this.hasBadCredentials = true;
    });
  }
}
