import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {PasswordValidators} from "../../utils/validators";

@Component({
  selector: 'app-new-article',
  templateUrl: './new-article.component.html',
  styleUrls: ['./new-article.component.scss']
})
export class NewArticleComponent implements OnInit {
  noFileChosen:string='Не е избран файл.'

  isSubmitButtonDisabled = true;
  pictureFilename: string = this.noFileChosen;
  private picture: File | null = null;


  newArticleForm = new FormGroup({
    heading: new FormControl('', [
      Validators.required,
      // Validators.minLength(10)
    ]),
    pictureFile:new FormControl('',Validators.required),
    pictureSource: new FormControl('', [
      Validators.required,
    ]),
    article: new FormControl('', [
      Validators.required,
      // Validators.minLength(100),
    ]),
    author: new FormControl('', [
      Validators.required,
    ]),
    categories: new FormControl('',Validators.required)
  })

// todo ask server for categories
  articleCategories = ['Анализи','Политика','Бизнес','Спорт'];

  constructor() { }

  ngOnInit(): void {
    this.newArticleForm
      .statusChanges
      .subscribe(formStatus => this.isSubmitButtonDisabled = formStatus == 'INVALID');

  }

  onSubmit(){
    console.log('submitting!')
    console.log(this.newArticleForm.value);
  }

  onFileChange(e: any) {
    this.picture = e.target.files[0];
    this.pictureFilename = this.picture ? this.picture.name : this.noFileChosen;
  }
}
