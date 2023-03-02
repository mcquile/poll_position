import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompletedPollsComponent } from './completed-polls.component';

describe('CompletedPollsComponent', () => {
  let component: CompletedPollsComponent;
  let fixture: ComponentFixture<CompletedPollsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompletedPollsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompletedPollsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
