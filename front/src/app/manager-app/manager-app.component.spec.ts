import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerAppComponent } from './manager-app.component';

describe('ManagerAppComponent', () => {
  let component: ManagerAppComponent;
  let fixture: ComponentFixture<ManagerAppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManagerAppComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerAppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
