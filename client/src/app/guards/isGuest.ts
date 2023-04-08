import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {tap} from 'rxjs';
import {UserService} from "../core/user-service/user.service";

export const isGuest = () => {
  let router = inject(Router);
  let userService = inject(UserService)

  if (userService.getCurrentUser()) {
   router.navigateByUrl("/");
    return false;
  }
  return true;
}
