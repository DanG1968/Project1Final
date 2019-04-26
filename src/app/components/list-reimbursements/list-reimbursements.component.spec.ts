import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListReimbursementsComponent } from './list-reimbursements.component';

describe('ListReimbursementsComponent', () => {
  let component: ListReimbursementsComponent;
  let fixture: ComponentFixture<ListReimbursementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListReimbursementsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListReimbursementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
