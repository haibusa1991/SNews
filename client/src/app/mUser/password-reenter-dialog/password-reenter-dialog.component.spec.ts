import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordReenterDialogComponent } from './password-reenter-dialog.component';

describe('PasswordReenterDialogComponent', () => {
  let component: PasswordReenterDialogComponent;
  let fixture: ComponentFixture<PasswordReenterDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PasswordReenterDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PasswordReenterDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
