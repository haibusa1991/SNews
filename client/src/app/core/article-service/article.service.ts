import {Injectable} from '@angular/core';
import {articleEndpoints, userEndpoints} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {switchMap} from "rxjs";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {FormGroup} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private http: HttpClient) {
  }

  getToken$() {
    return this.http.get(userEndpoints['csrf'],
      {responseType: 'text', withCredentials: true},)
  }

  postNewArticle$(inputForm: FormGroup, pictureFile: File) {
    // this.getToken$().pipe(
    //   switchMap(() => {
    //     return this.http.post(articleEndpoints['newArticle'], formBody, {
    //       responseType: 'text' as const,
    //       withCredentials: true,
    //       observe: 'response'
    //     })
    //   })
    // ).subscribe({
    //   next: n => {
    //     console.log(n)
    //   },
    //   error: e => {
    //     console.log(e);
    //   }
    // })


    let body = new FormData();
    let input = inputForm.controls;
    body.append('heading', input['heading'].value);
    body.append('pictureSource', input['pictureSource'].value);
    body.append('article', input['article'].value);
    body.append('author', input['author'].value);
    body.append('categories', input['categories'].value);
    body.append('pictureFile', pictureFile);

    this.getToken$().pipe(
      switchMap(() => {
        return this.http.post(articleEndpoints['newArticle'], body, {
          responseType: 'text' as const,
          withCredentials: true,
          observe: 'response'
        });
      })
    )
      .subscribe({
        next: n => {
          console.log(n)
        },
        error: e => {
          console.log(e);
        }
      });
  }
}
