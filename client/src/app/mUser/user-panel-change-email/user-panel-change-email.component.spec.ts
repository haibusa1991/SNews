import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPanelChangeEmailComponent } from './user-panel-change-email.component';

describe('UserPanelChangeEmailComponent', () => {
  let component: UserPanelChangeEmailComponent;
  let fixture: ComponentFixture<UserPanelChangeEmailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPanelChangeEmailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserPanelChangeEmailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
