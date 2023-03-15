import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsSearchResultsComponent } from './news-search-results.component';

describe('NewsSearchResultsComponent', () => {
  let component: NewsSearchResultsComponent;
  let fixture: ComponentFixture<NewsSearchResultsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewsSearchResultsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewsSearchResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
