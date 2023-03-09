import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../core/user-service/user.service";

@Component({
  selector: 'app-forgotten-password',
  templateUrl: './forgotten-password.component.html',
  styleUrls: ['./forgotten-password.component.scss']
})
export class ForgottenPasswordComponent implements OnInit {

  forgottenPasswordForm = new FormGroup({
      email: new FormControl('', Validators.email)
    }
  )

  isSubmitButtonDisabled: boolean = true;
  hasSentForm: boolean = false;

  constructor(private router: Router,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.forgottenPasswordForm
      .statusChanges
      .subscribe(formStatus => this.isSubmitButtonDisabled = formStatus == 'INVALID');
  }

  onSubmit() {
    this.userService.recoverPassword$(this.forgottenPasswordForm.controls.email.value!).subscribe();
    this.hasSentForm = true;
  }

  navigateToHome() {
    this.router.navigateByUrl('/');
  }

}
