import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";


export class PasswordValidators {
   static uppercase(): ValidatorFn {
     return (c: AbstractControl): ValidationErrors | null => {
       if (!c.value) {
         return null;
       }

       return /[A-Z]+/.test(c.value) ? null : {noUppercase: true};
     }
   }

   static lowercase(): ValidatorFn {
     return (c: AbstractControl): ValidationErrors | null => {
       if (!c.value) {
         return null;
       }

       return /[a-z]+/.test(c.value) ? null : {noLowercase: true};
     }
   }

   static numeric(): ValidatorFn {
    return (c: AbstractControl): ValidationErrors | null => {
      if (!c.value) {
        return null;
      }

      return /[0-9]+/.test(c.value) ? null :{noNumeric: true};
    }
  }
}


