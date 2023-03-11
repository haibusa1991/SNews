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
  isFileTooBig = false;
  private picture: File | null = null;


  newArticleForm = new FormGroup({
    heading: new FormControl('', [
      Validators.required,
      Validators.minLength(10)
    ]),
    pictureFile:new FormControl(''),
    pictureSource: new FormControl('', [
      Validators.required,
    ]),
    article: new FormControl('', [
      Validators.required,
      Validators.minLength(100),
    ]),
    author: new FormControl('', [
      Validators.required,
    ]),
    category: new FormControl('',Validators.required)
  })

// todo ask server for categories
  articleCategories = ['']

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(){
    console.log('submitting!')
  }

  onFileChange(e: any) {
    this.picture = e.target.files[0];

    this.isFileTooBig = this.picture ? this.picture.size > 2048 : false;
    this.pictureFilename = this.picture ? this.picture.name : this.noFileChosen;
  }
}
