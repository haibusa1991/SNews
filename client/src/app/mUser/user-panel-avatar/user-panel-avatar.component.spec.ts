import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPanelAvatarComponent } from './user-panel-avatar.component';

describe('UserPanelAvatarComponent', () => {
  let component: UserPanelAvatarComponent;
  let fixture: ComponentFixture<UserPanelAvatarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserPanelAvatarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserPanelAvatarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
