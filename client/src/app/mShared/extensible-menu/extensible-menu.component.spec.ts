import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExtensibleMenuComponent } from './extensible-menu.component';

describe('ExtensibleMenuComponent', () => {
  let component: ExtensibleMenuComponent;
  let fixture: ComponentFixture<ExtensibleMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExtensibleMenuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExtensibleMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
