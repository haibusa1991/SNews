import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ArticleService} from "../../core/article-service/article.service";
import {articleCategories} from "../../utils/snewsConstants";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-article',
  templateUrl: './new-article.component.html',
  styleUrls: ['./new-article.component.scss']
})
export class NewArticleComponent implements OnInit {
  noFileChosen: string = 'Не е избран файл.'

  isSubmitButtonDisabled = true;
  pictureFilename: string = this.noFileChosen;
  private pictureFile: File | null = null;


  newArticleForm = new FormGroup({
    heading: new FormControl('my heading', [
      Validators.required,
      // Validators.minLength(10)
    ]),
    pictureFile: new FormControl('', Validators.required),
    pictureSource: new FormControl('my picture source', [
      Validators.required,
    ]),
    article: new FormControl('my article', [
      Validators.required,
      // Validators.minLength(100),
    ]),
    author: new FormControl('my author', [
      Validators.required,
    ]),
    categories: new FormControl('', Validators.required)
  })

// todo ask server for categories
  articleCategories!: string[];

  constructor(private articleService: ArticleService, private router: Router) {
  }

  ngOnInit(): void {
    this.newArticleForm
      .statusChanges
      .subscribe(formStatus => this.isSubmitButtonDisabled = formStatus == 'INVALID');

    this.articleService.getArticleCategories$().subscribe(e => {
      let result: string[] = [];
      for (let category of e) {
        if (articleCategories[category]) {
          result.push(articleCategories[category]);
          continue;
        }
        result.push(category);
      }

      this.articleCategories = result;
    })
  }

  onSubmit() {
    this.articleService.postNewArticle$(this.newArticleForm, this.pictureFile!).subscribe(link => this.router.navigateByUrl(`/news/article/${link}`));
  }

  onFileChange(e: any) {
    this.pictureFile = e.target.files[0];
    this.pictureFilename = this.pictureFile ? this.pictureFile.name : this.noFileChosen;
  }
}
