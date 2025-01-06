
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);



import BookHistoryView from "./components/BookHistoryView"
import BookHistoryViewDetail from "./components/BookHistoryViewDetail"

import LoanSummaryView from "./components/LoanSummaryView"
import LoanSummaryViewDetail from "./components/LoanSummaryViewDetail"
import LoanDetailsView from "./components/LoanDetailsView"
import LoanDetailsViewDetail from "./components/LoanDetailsViewDetail"

export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [

            {
                path: '/libraryManagements/bookHistories',
                name: 'BookHistoryView',
                component: BookHistoryView
            },
            {
                path: '/libraryManagements/bookHistories/:id',
                name: 'BookHistoryViewDetail',
                component: BookHistoryViewDetail
            },

            {
                path: '/loanManagements/loanSummaries',
                name: 'LoanSummaryView',
                component: LoanSummaryView
            },
            {
                path: '/loanManagements/loanSummaries/:id',
                name: 'LoanSummaryViewDetail',
                component: LoanSummaryViewDetail
            },
            {
                path: '/loanManagements/loanDetails',
                name: 'LoanDetailsView',
                component: LoanDetailsView
            },
            {
                path: '/loanManagements/loanDetails/:id',
                name: 'LoanDetailsViewDetail',
                component: LoanDetailsViewDetail
            },


    ]
})
