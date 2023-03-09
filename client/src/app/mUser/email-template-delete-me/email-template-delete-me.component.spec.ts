import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailTemplateDeleteMeComponent } from './email-template-delete-me.component';

describe('EmailTemplateDeleteMeComponent', () => {
  let component: EmailTemplateDeleteMeComponent;
  let fixture: ComponentFixture<EmailTemplateDeleteMeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmailTemplateDeleteMeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmailTemplateDeleteMeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
