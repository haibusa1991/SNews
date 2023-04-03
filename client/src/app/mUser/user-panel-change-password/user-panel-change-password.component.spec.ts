import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPanelChangePasswordComponent } from './user-panel-change-password.component';

describe('UserPanelChangePasswordComponent', () => {
  let component: UserPanelChangePasswordComponent;
  let fixture: ComponentFixture<UserPanelChangePasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPanelChangePasswordComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserPanelChangePasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
