import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsCategory } from './news-category.component';

describe('NewsSearchResultsComponent', () => {
  let component: NewsCategory;
  let fixture: ComponentFixture<NewsCategory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewsCategory ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsCategory);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
