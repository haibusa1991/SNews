import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {tap} from 'rxjs';
import {UserService} from "../core/user-service/user.service";

export const isModerator = () => {
  let router = inject(Router);
  let userService = inject(UserService)

  let currentUser = userService.getCurrentUser();
  let isMod= currentUser && currentUser.roles.includes("MODERATOR");

  if(!currentUser){
    router.navigateByUrl("/user/login");
    return false;
  }

  if (!isMod) {
    router.navigateByUrl("/");
    return false;
  }
  return true;
}
