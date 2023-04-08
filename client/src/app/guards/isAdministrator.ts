import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {tap} from 'rxjs';
import {UserService} from "../core/user-service/user.service";

export const isAdministrator = () => {
  let router = inject(Router);
  let userService = inject(UserService)

  let currentUser = userService.getCurrentUser();
  let isAdmin= currentUser && currentUser.roles.includes("ADMINISTRATOR");

  if(!currentUser){
    router.navigateByUrl("/user/login");
    return false;
  }

  if (!isAdmin) {
    router.navigateByUrl("/");
    return false;
  }
  return true;
}
