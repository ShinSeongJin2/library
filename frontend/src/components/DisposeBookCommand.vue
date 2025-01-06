<template>
    <v-card outlined>
        <v-card-title>
            DisposeBook
        </v-card-title>

        <v-card-text>
            <String label="DisposeReason" v-model="value.disposeReason" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="disposeBook"
            >
                DisposeBook
            </v-btn>
            
            <v-btn
                    color="primary"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'DisposeBookCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.disposeReason = '';
        },
        watch: {
        },
        methods: {
            disposeBook() {
                this.$emit('disposeBook', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

